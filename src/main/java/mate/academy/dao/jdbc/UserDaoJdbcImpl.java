package mate.academy.dao.jdbc;

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
import mate.academy.dao.UserDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lb.Dao;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {

    @Override
    public User create(User user) {
        String insertUser = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertUserStatement = connection
                        .prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
            insertUserStatement.setString(1, user.getName());
            insertUserStatement.setString(2, user.getLogin());
            insertUserStatement.setString(3, user.getPassword());
            insertUserStatement.executeUpdate();
            ResultSet resultSet = insertUserStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                user.setId(userId);
                insertUserRoles(user, connection);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating user " + user
                    + " is failed", e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        String updateUser = "UPDATE users SET name = ?, login = ?, password = ? "
                + "WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateUserStatement
                        = connection.prepareStatement(updateUser)) {
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getLogin());
            updateUserStatement.setString(3, user.getPassword());
            updateUserStatement.setLong(4, user.getId());
            updateUserStatement.executeUpdate();
            updateUsersRoles(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Updating user " + user
                    + " is failed", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String selectUsers = "SELECT * FROM users "
                + "WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectUsersStatement
                        = connection.prepareStatement(selectUsers)) {
            selectUsersStatement.setLong(1, id);
            ResultSet resultSet = selectUsersStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(name, login, password);
                user.setId(id);
                user.setRoles(getUserRoles(id, connection));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting user with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String selectUser = "SELECT * FROM users WHERE login = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectUserStatement
                        = connection.prepareStatement(selectUser)) {
            selectUserStatement.setString(1, login);
            ResultSet resultSet = selectUserStatement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                User user = new User(name, login, password);
                user.setId(userId);
                user.setRoles(getUserRoles(userId, connection));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting user by user login="
                    + login + " is failed", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String selectUsers = "SELECT * FROM users WHERE deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectUsersStatement
                        = connection.prepareStatement(selectUsers)) {
            ResultSet resultSet = selectUsersStatement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(name, login, password);
                user.setId(userId);
                user.setRoles(getUserRoles(userId, connection));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DataProcessingException("Getting users from DB is failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String updateUsers = "UPDATE users SET deleted = true WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateUsersStatement
                        = connection.prepareStatement(updateUsers)) {

            updateUsersStatement.setLong(1, id);
            if (updateUsersStatement.executeUpdate() == 1) {
                return false;
            }
            deleteUsersRoles(id, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting user with id="
                    + id + " is failed", e);
        }
        return true;
    }

    private void getRoleId(Role role, Connection connection)
            throws SQLException {
        String selectRoleId = "SELECT role_id FROM roles WHERE role_name = ?;";
        try (PreparedStatement selectRoleIdStatement
                     = connection.prepareStatement(selectRoleId)) {
            selectRoleIdStatement.setString(1, role.getRoleName().name());
            ResultSet resultSet = selectRoleIdStatement.executeQuery();
            if (resultSet.next()) {
                role.setId(resultSet.getLong(1));
            }
        }
    }

    private Set<Role> getUserRoles(Long userId, Connection connection)
            throws SQLException {
        String selectRoleId = "SELECT roles.role_id, role_name FROM users_roles "
                + "JOIN roles ON users_roles.role_id = roles.role_id WHERE user_id = ?;";
        Set<Role> roles = new HashSet<>();
        try (PreparedStatement selectRoleIdStatement
                     = connection.prepareStatement(selectRoleId)) {
            selectRoleIdStatement.setLong(1, userId);
            ResultSet resultSet = selectRoleIdStatement.executeQuery();

            while (resultSet.next()) {
                Long roleId = resultSet.getLong(1);
                String roleName = resultSet.getString(2);
                Role role = Role.of(roleName);
                role.setId(roleId);
                roles.add(role);
            }
        }
        return roles;
    }

    private void insertUserRoles(User user, Connection connection)
            throws SQLException {
        String insertUsersRoles = "INSERT INTO users_roles (user_id, role_id) VALUES (?, "
                + "(SELECT role_id FROM roles WHERE role_name = ?));";
        try (PreparedStatement insertStatement
                     = connection.prepareStatement(insertUsersRoles)) {
            for (Role role : user.getRoles()) {
                insertStatement.setLong(1, user.getId());
                insertStatement.setString(2, role.getRoleName().name());
                insertStatement.executeUpdate();
                getRoleId(role, connection);
            }
        }
    }

    private void updateUsersRoles(User user, Connection connection)
            throws SQLException {
        String deleteUsersRoles = "DELETE FROM users_roles WHERE user_id = ?;";
        try (PreparedStatement deleteUsersRolesStatement
                     = connection.prepareStatement(deleteUsersRoles)) {
            deleteUsersRolesStatement.setLong(1, user.getId());
            deleteUsersRolesStatement.executeUpdate();
            insertUserRoles(user, connection);
        }
    }

    private void deleteUsersRoles(Long userId, Connection connection)
            throws SQLException {
        String deleteUsersRoles = "DELETE FROM users_roles WHERE user_id = ?;";
        try (PreparedStatement deleteUsersRolesStatement
                     = connection.prepareStatement(deleteUsersRoles)) {
            deleteUsersRolesStatement.setLong(1, userId);
            deleteUsersRolesStatement.executeUpdate();
        }
    }
}
