package io.github.sergeivisotsky.metadata.selector.jdbc.sqlgen;

import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link TimeUnitFormatter}.
 *
 * @author Sergei Visotsky
 */
public class TimeUnitFormatterTest {

    @Test
    public void shouldFormatDate() {
        //given
        final Date date = new Date(19700102);

        //when
        String actual = TimeUnitFormatter.formatDate(date);

        //then
        assertEquals("1970-01-01", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithIllegalArgumentExceptionWhenIncorrectDateTypePassed() {
        //given
        final String date = "19700102";

        //when
        TimeUnitFormatter.formatDate(date);
    }

    @Test
    public void shouldFormatDateTime() {
        //given
        final Date date = new Date(19700102);

        //when
        String actual = TimeUnitFormatter.formatDateTime(date);

        //then
        assertEquals("1970-01-01T05:28:20.102", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithIllegalArgumentExceptionWhenIncorrectDateTimeTypePassed() {
        //given
        final String date = "19700102";

        //when
        TimeUnitFormatter.formatDateTime(date);
    }

    @Test
    public void shouldFormatTime() {
        //given
        final Time time = Time.valueOf("12:00:03");

        //when
        String actual = TimeUnitFormatter.formatTime(time);

        //then
        assertEquals("110003", actual);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithIllegalArgumentExceptionWhenIncorrectTimeTypePassed() {
        //given
        final String timeAsString = "12:00:03";

        //when
        TimeUnitFormatter.formatTime(timeAsString);
    }
}
