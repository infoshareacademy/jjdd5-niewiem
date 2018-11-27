package com.infoshareacademy.niewiem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HallTest {

    @Test
    public void newHallNameIsTheSameAsInConstructor(){
        // GIVEN
        Hall hall = new Hall("Hall");

        // WHEN
        String result = hall.getName();

        // THEN
        assertThat(result).isEqualTo("Hall");
    }
    @Test
    public void newHallHasEmptyTableList(){
        // GIVEN
        Hall hall = new Hall("Hall");

        // WHEN
        List<Table> result = hall.getTableList();

        // THEN
        assertThat(result)
                .isNotNull()
                .isInstanceOf(new ArrayList<Table>().getClass())
                .isEmpty();
    }

}