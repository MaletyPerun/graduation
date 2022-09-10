package ru.graduation.service;

import org.junit.jupiter.api.Test;
import ru.graduation.error.DataConflictException;
import ru.graduation.util.Util;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.graduation.util.validation.ValidationUtil.inTime;

class ProfileServiceTest {

    private static final LocalTime STARTTIME = LocalTime.of(11, 0);
    private static final LocalTime INTIME = LocalTime.of(12, 0);
    private static final LocalTime ENDTIME = LocalTime.of(15, 0);
    private static final LocalTime OUTTIME = LocalTime.of(16, 0);


//    @Test
//    void prepareAndVoteStartTime() {
//        assertThrows(DataConflictException.class, () -> inTime(Util.isBetweenHalfOpen(STARTTIME)));
//    }
//
//    @Test
//    void prepareAndInTime() {
//        assertThrows(DataConflictException.class, () -> inTime(Util.isBetweenHalfOpen(INTIME)));
//    }
//
//    @Test
//    void prepareAndEndTime() {
//        inTime(Util.isBetweenHalfOpen(ENDTIME));
//    }
//
//    @Test
//    void prepareAndOutTime() {
//        inTime(Util.isBetweenHalfOpen(OUTTIME));
//    }
}