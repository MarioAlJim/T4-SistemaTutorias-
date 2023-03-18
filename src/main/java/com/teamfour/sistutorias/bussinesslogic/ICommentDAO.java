package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.Comment;

import java.sql.SQLException;

public interface ICommentDAO {

    public int register(Comment comment) throws SQLException;

    public int update(Comment comment) throws SQLException;

    public int delete(Comment comment) throws SQLException;

}
