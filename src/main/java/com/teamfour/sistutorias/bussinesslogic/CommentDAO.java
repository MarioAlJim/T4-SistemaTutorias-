package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CommentDAO implements ICommentDAO {
    @Override
    public int register(Comment comment) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = comment.getDescription();
        int register = comment.getRegister();
        String query = "INSERT INTO comment (description, register_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, register);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int update(Comment comment) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        String description = comment.getDescription();
        int register = comment.getRegister();
        int id = comment.getComment_id();
        String query = "UPDATE comment SET description = ?, register_id = ? WHERE comment_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, register);
            statement.setInt(3, id);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public int delete(Comment comment) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int insertedFiles = 0;
        Connection connection = dataBaseConnection.getConnection();
        int id = comment.getComment_id();
        String query = "DELETE FROM comment WHERE comment_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            insertedFiles = statement.executeUpdate();
        return insertedFiles;
    }

    @Override
    public Comment getCommentFromRegister(int registerId) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        Comment comment = new Comment();
        String query = "SELECT * FROM comment WHERE register = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, registerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            comment.setComment_id(resultSet.getInt("comment_id"));
            comment.setDescription(resultSet.getString("description"));
            comment.setRegister(resultSet.getInt("register"));
        }
        return comment;
    }
}
