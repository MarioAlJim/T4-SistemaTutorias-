package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EE;
import com.teamfour.sistutorias.domain.EducativeProgram;
import com.teamfour.sistutorias.domain.Group;
import com.teamfour.sistutorias.domain.Teacher;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {

    @Test
    void registerGroup() {
        int result = 0;
        GroupDAO groupDAO = new GroupDAO();
        EE ee = new EE();
        ee.setIdEe(1);
        Teacher teacher = new Teacher();
        teacher.setPersonalNumber(789456);
        Group group = new Group();
        group.setNrc(85296);
        group.setEe(ee);
        group.setTeacher(teacher);
        EducativeProgram educativeProgram = new EducativeProgram();
        educativeProgram.setIdEducationProgram(1);
        group.setEducationProgram(educativeProgram);
        try {
            result = groupDAO.registerGroup(group);
        }catch (SQLException sqlException)  {
            Logger.getLogger(GroupDAOTest.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        assertTrue(result == -1);
    }
}