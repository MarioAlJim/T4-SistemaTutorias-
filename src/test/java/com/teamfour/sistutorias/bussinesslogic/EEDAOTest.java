package com.teamfour.sistutorias.bussinesslogic;

import com.teamfour.sistutorias.domain.EE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EEDAOTest {
    EEDAO eedao;
    ArrayList<EE> ees;
    ArrayList<EE> registeredEEs;
    EE ee;
    EE ee1;
    EE ee2;
    @BeforeEach
    void setUp() {
        eedao = new EEDAO();
        ees = new ArrayList<>();
        registeredEEs = new ArrayList<>();
        ee = new EE();
        ee1 = new EE();
        ee2 = new EE();
    }

    @Test
    public void getEEs() throws SQLException {
        ee1.setName("Desarrollo de sistemas en red");
        registeredEEs.add(ee1);
        ee2.setName("Desarrollo de aplicaciones");
        registeredEEs.add(ee2);

        ees = eedao.getEEsByProgram(1);
        boolean isValid = true;
        int iterator = 0;
        for(EE eeUV : ees) {
            if(!eeUV.getName().equals(registeredEEs.get(iterator).getName())) {
                isValid = false;
            }
            iterator++;
        }
        assertTrue(isValid);
    }
    @Test
    public void registerEE() throws SQLException {
        ee.setName("registrar ee PRUEBA");
        boolean flag = eedao.register(ee);
        assertTrue(flag);
    }

    @Test
    public void updateEE() throws SQLException {
        ee.setIdEe(1);
        ee.setName("actualizar ee PRUEBA");
        boolean flag = eedao.update(ee);
        assertTrue(flag);
    }

    @Test
    public void deleteEE() throws SQLException {
        ee.setIdEe(1);
        boolean flag = eedao.delete(ee);
        assertTrue(flag);
    }
}