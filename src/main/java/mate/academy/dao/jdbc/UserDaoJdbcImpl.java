package mate.academy.dao.jdbc;

import mate.academy.dao.UserDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lb.Dao;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String selectUser = "SELECT * FROM users WHERE login = ? AND deleted = false;";
        String selectUsersRoles = "SELECT roles.role_id, roles.role_name " +
                "FROM roles " +
                "INNER JOIN users_roles " +
                "ON roles.role_id = users_roles.role_id " +
                "WHERE users_roles.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectUserStatement = connection.prepareStatement(selectUser);
            selectUserStatement.setString(1, login);
            ResultSet resultSet = selectUserStatement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                PreparedStatement selectUsersRolesStatement = connection.prepareStatement(selectUsersRoles);
                selectUsersRolesStatement.setLong(1, userId);
                ResultSet resultSet1 = selectUsersRolesStatement.executeQuery();
                Set<Role> roleSet = new HashSet<>();
                while (resultSet1.next()) {
                    Long roleId = resultSet1.getLong("roles.role_id");
                    String roleName = resultSet1.getString("roles.role_name");
                    Role role = Role.of(roleName);
                    role.setId(roleId);
                    roleSet.add(role);
                }
                User user = new User(name, login, password);
                user.setId(userId);
                user.setRoles(roleSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting user by user login="
                    + login + " is failed", e);
        }
    }

    @Override
    public User create(User user) {
        String insertUser = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
        String selectRoleId = "SELECT role_id FROM roles WHERE role_name = ?;";
        String insertUsersRoles = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insertUserStatement = connection.prepareStatement(insertUser,
                    Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getName());
            insertUserStatement.setString(2, user.getLogin());
            insertUserStatement.setString(3, user.getPassword());
            insertUserStatement.executeUpdate();
            ResultSet resultSet = insertUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                user.setId(userId);

//                String quer = "INSERT INTO users_roles (user_id, role_id) VALUES (?, " +
//                        "SELECT role_id FROM roles WHERE role_name = ?);";
//                for (Role role : user.getRoles()) {
//                    PreparedStatement selectRoleIdStatement = connection.prepareStatement(quer);
//                    selectRoleIdStatement.setLong(1, userId);
//                    selectRoleIdStatement.setString(2, role.getRoleName().name());
//                    selectRoleIdStatement.executeUpdate();
//                      role.setId(roleId);
//                }

                for (Role role : user.getRoles()) {
                    PreparedStatement selectRoleIdStatement = connection.prepareStatement(selectRoleId);
                    selectRoleIdStatement.setString(1, role.getRoleName().name());
                    ResultSet resultSet1 = selectRoleIdStatement.executeQuery();
                    if (resultSet1.next()) {
                        Long roleId = resultSet1.getLong("role_id");
                        PreparedStatement insertUsersRolesStatement = connection.prepareStatement(insertUsersRoles);
                        insertUsersRolesStatement.setLong(1, userId);
                        insertUsersRolesStatement.setLong(2, roleId);
                        insertUsersRolesStatement.executeUpdate();
                        role.setId(roleId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating user " + user
                    + " is failed", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        String updateUser = "UPDATE users SET name = ?, login = ?, password = ? WHERE user_id = ? AND deleted = false;";
        String deleteUsersRoles = "DELETE FROM users_roles WHERE user_id = ?;";
        String selectRoleId = "SELECT role_id FROM roles WHERE role_name = ?;";
        String insertUsersRoles = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUser);
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getLogin());
            updateUserStatement.setString(3, user.getPassword());
            updateUserStatement.setLong(4, user.getId());
            updateUserStatement.executeUpdate();
            for (Role role : user.getRoles()) {
                PreparedStatement deleteUsersRolesStatement = connection.prepareStatement(deleteUsersRoles);
                deleteUsersRolesStatement.setLong(1, user.getId());
                deleteUsersRolesStatement.executeUpdate();

                PreparedStatement selectRoleIdStatement = connection.prepareStatement(selectRoleId);
                selectRoleIdStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectRoleIdStatement.executeQuery();
                if (resultSet.next()) {
                    PreparedStatement insertUsersRolesStatement = connection.prepareStatement(insertUsersRoles);
                    insertUsersRolesStatement.setLong(1, user.getId());
                    insertUsersRolesStatement.setLong(2, resultSet.getLong("role_id"));
                    insertUsersRolesStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Updating user " + user
                    + " is failed", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String selectUsersRoles = "SELECT users.user_id, users.name, users.login, users.password, " +
                "roles.role_id, roles.role_name " +
                "FROM users " +
                "INNER JOIN users_roles " +
                "ON users.user_id = users_roles.user_id " +
                "INNER JOIN roles " +
                "ON roles.role_id = users_roles.role_id " +
                "WHERE users.user_id = ? AND users.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectUsersRolesStatement = connection.prepareStatement(selectUsersRoles);
            selectUsersRolesStatement.setLong(1, id);
            ResultSet resultSet = selectUsersRolesStatement.executeQuery();
            if (resultSet.next()) {
                User user = retrieveUserFromResultSet(resultSet);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting user with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String selectUsers = "SELECT * FROM users WHERE deleted = false;";
        String selectUsersRoles = "SELECT users.user_id, users.name, users.login, users.password, " +
                "roles.role_id, roles.role_name " +
                "FROM users " +
                "INNER JOIN users_roles " +
                "ON users.user_id = users_roles.user_id " +
                "INNER JOIN roles " +
                "ON roles.role_id = users_roles.role_id " +
                "WHERE users.user_id = ? AND users.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectUsersStatement = connection.prepareStatement(selectUsers);
            ResultSet resultSet = selectUsersStatement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                PreparedStatement selectUsersRolesStatement = connection.prepareStatement(selectUsersRoles);
                selectUsersRolesStatement.setLong(1, userId);
                ResultSet resultSet1 = selectUsersRolesStatement.executeQuery();
                if (resultSet1.next()) {
                    User user = retrieveUserFromResultSet(resultSet1);
                    user.setId(userId);
                    userList.add(user);
                }
            }
            return userList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting users from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateUsers = "UPDATE users SET deleted = true WHERE user_id = ?;";
        String deleteUsersRoles = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement updateUsersStatement = connection.prepareStatement(updateUsers);
            updateUsersStatement.setLong(1, id);
            if (updateUsersStatement.executeUpdate() == 1) {
                return false;
            }
            PreparedStatement deleteUsersRolesStatement = connection.prepareStatement(deleteUsersRoles);
            deleteUsersRolesStatement.setLong(1, id);
            deleteUsersRolesStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting user with id="
                    + id + " is failed", e);
        }
        return true;
    }

    private User retrieveUserFromResultSet(ResultSet resultSet)
            throws SQLException {
        String name = resultSet.getString("users.name");
        String login = resultSet.getString("users.login");
        String password = resultSet.getString("users.password");
        Set<Role> roleSet = new HashSet<>();
        do {
            Role role = Role.of(resultSet.getString("roles.role_name"));
            role.setId(resultSet.getLong("roles.role_id"));
            roleSet.add(role);
        } while (resultSet.next());
        User user = new User(name, login, password);
        user.setRoles(roleSet);
        return user;
    }
}
