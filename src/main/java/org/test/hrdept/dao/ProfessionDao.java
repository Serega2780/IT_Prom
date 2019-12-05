package org.test.hrdept.dao;

import org.test.hrdept.domain.Profession;

import java.util.List;

public interface ProfessionDao {

    void insertProfession(Profession Profession);

    Profession selectProfessionById(int id);

    List<Profession> selectAllProfessions();

    Profession selectProfessionByName(String name);

    boolean deleteProfession(int id);

    void updateProfession(Profession Profession);
}
