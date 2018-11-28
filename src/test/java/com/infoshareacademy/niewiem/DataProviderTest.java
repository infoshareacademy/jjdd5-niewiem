package com.infoshareacademy.niewiem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for fetching and filtering data")
class DataProviderTest {

    @Test
    @DisplayName("Should properly filter list for one club")
    void readFromTablesForOneClub() {
        // given
        DataProvider subject = new DataProvider();
        Set<String> testList = new HashSet<>();
        Table expectedTable = new Table(1, TableType.POOL, "Green");

        testList.add("1, POOL, Green");
        testList.add("2, POOL, Red");

        // when
        Set<Table> actual = subject.readFromTables("Green", testList);

        // then
        assertThat(actual).containsOnly(expectedTable);
    }

    @Test
    @DisplayName("Should properly filter list when club is duplicated")
    void readFromTablesWhenClubDuplicated() {
        // given
        DataProvider subject = new DataProvider();
        Set<String> testList = new HashSet<>();
        Table expectedTable = new Table(1, TableType.POOL, "Green");

        testList.add("1, POOL, Green");
        testList.add("1, POOL, Green");

        // when
        Set<Table> actual = subject.readFromTables("Green", testList);

        // then
        assertThat(actual).containsOnly(expectedTable);
        assertThat(actual.size()).isEqualTo(1);
    }





}