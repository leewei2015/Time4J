package net.time4j.history;

import net.time4j.CalendarUnit;
import net.time4j.PlainDate;
import net.time4j.PlainTimestamp;
import net.time4j.format.Leniency;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class EraFormatTest {

    @Test
    public void printStdEraName() {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy GGGG HH:mm", PatternType.CLDR).build();
        assertThat(
            formatter.format(PlainTimestamp.of(1582, 10, 14, 0, 0)),
            is("4. Oktober 1582 n. Chr. 00:00"));
    }

    @Test
    public void parseStdEraName() throws ParseException {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy GGGG HH:mm", PatternType.CLDR).build();
        assertThat(
            formatter.parse("4. Oktober 1582 n. Chr. 00:00"),
            is(PlainTimestamp.of(1582, 10, 14, 0, 0)));
    }

    @Test
    public void printAlternative() {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy G HH:mm", PatternType.CLDR)
                .build()
                .withAlternativeEraNames();
        assertThat(
            formatter.format(PlainTimestamp.of(1582, 10, 14, 0, 0)),
            is("4. Oktober 1582 u. Z. 00:00"));
    }

    @Test
    public void parseAlternative() throws ParseException {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy G HH:mm", PatternType.CLDR)
                .build()
                .withAlternativeEraNames();
        assertThat(
            formatter.parse("4. Oktober 1582 u. Z. 00:00"),
            is(PlainTimestamp.of(1582, 10, 14, 0, 0)));
    }

    @Test
    public void printGermanWithLatinEra() {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy GGGG HH:mm", PatternType.CLDR)
                .build()
                .withLatinEraNames();
        assertThat(
            formatter.format(PlainTimestamp.of(1582, 10, 14, 0, 0)),
            is("4. Oktober 1582 Anno Domini 00:00"));
    }

    @Test
    public void parseGermanWithLatinEra() throws ParseException {
        ChronoFormatter<PlainTimestamp> formatter =
            ChronoFormatter.setUp(PlainTimestamp.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy GGGG HH:mm", PatternType.CLDR)
                .build()
                .withLatinEraNames();
        assertThat(
            formatter.parse("4. Oktober 1582 Anno Domini 00:00"),
            is(PlainTimestamp.of(1582, 10, 14, 0, 0)));
    }

    @Test
    public void printJulian() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.GERMAN)
                .addPattern("d. MMMM yyyy G", PatternType.CLDR)
                .build()
                .with(ChronoHistory.PROLEPTIC_JULIAN);
        assertThat(
            formatter.format(PlainDate.of(1752, 9, 13)),
            is("2. September 1752 n. Chr."));
    }

    @Test
    public void parseJulian() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.GERMAN)
                .addPattern("d. MMMM yyyy G", PatternType.CLDR)
                .build()
                .with(ChronoHistory.PROLEPTIC_JULIAN);
        assertThat(
            formatter.parse("2. September 1752 n. Chr."),
            is(PlainDate.of(1752, 9, 13)));
    }

    @Test
    public void printEngland1() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy G", PatternType.CLDR)
                .build()
                .withGregorianCutOver(PlainDate.of(1752, 9, 14));
        assertThat(
            formatter.format(PlainDate.of(1752, 9, 13)),
            is("2. September 1752 n. Chr."));
    }

    @Test
    public void parseEngland1() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.GERMANY)
                .addPattern("d. MMMM yyyy G", PatternType.CLDR)
                .build()
                .withGregorianCutOver(PlainDate.of(1752, 9, 14));
        assertThat(
            formatter.parse("2. September 1752 n. Chr."),
            is(PlainDate.of(1752, 9, 13)));
    }

    @Test
    public void printEngland2() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM G yyyy", PatternType.CLDR)
                .build();
        assertThat(
            formatter.format(PlainDate.of(1752, 9, 13)),
            is("2. September AD 1752"));
    }

    @Test
    public void parseEngland2() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM G yyyy", PatternType.CLDR)
                .build();
        assertThat(
            formatter.parse("2. September AD 1752"),
            is(PlainDate.of(1752, 9, 13)));
    }

    @Test
    public void printEngland3() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        assertThat(
            formatter.format(PlainDate.of(1603, 4, 3)),
            is("24. March Anno Domini 1602/03")); // death of Queen Elizabeth I.
    }

    @Test
    public void parseEngland3() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        assertThat(
            formatter.parse("24. March Anno Domini 1602/03"), // death of Queen Elizabeth I. (officially 1602)
            is(PlainDate.of(1603, 4, 3)));
        assertThat(
            formatter.parse("24. March Anno Domini 1602/3"), // test for year-of-era-part < 10
            is(PlainDate.of(1603, 4, 3)));
        assertThat(
            formatter.parse("24. March Anno Domini 1751/2"), // test for year-of-era-part < 10
            is(PlainDate.of(1752, 4, 4)));
    }

    @Test
    public void printEngland4() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        assertThat(
            formatter.format(PlainDate.of(1603, 4, 4)),
            is("25. March Anno Domini 1603")); // new year
    }

    @Test
    public void parseEngland4() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        assertThat(
            formatter.parse("25. March Anno Domini 1603"), // new year
            is(PlainDate.of(1603, 4, 4)));
        assertThat(
            formatter.parse("24. March Anno Domini 1603"), // death of Queen Elizabeth I. using year-of-era only
            is(PlainDate.of(1603, 4, 3)));
    }

    @Test(expected=ParseException.class) // due to trailing chars "/4"
    public void parseEngland5() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.UK)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        formatter.parse("24. March Anno Domini 1602/4"); // inplausible difference in year part
    }

    @Test
    public void printSweden1() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("sv", "SE"))
                .addPattern("d. MMMM yyyy GGGG", PatternType.CLDR)
                .build();
        assertThat(
            formatter.format(PlainDate.of(1712, 3, 11)),
            is("30. februari 1712 efter Kristus"));
    }

    @Test
    public void parseSweden1() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("sv", "SE"))
                .addPattern("d. MMMM yyyy GGGG", PatternType.CLDR)
                .build();
        assertThat(
            formatter.parse("30. februari 1712 efter Kristus"),
            is(PlainDate.of(1712, 3, 11)));
    }

    @Test
    public void printSweden2() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("sv"))
                .addPattern("d. MMMM yyyy", PatternType.CLDR)
                .build()
                .with(ChronoHistory.ofSweden());
        assertThat(
            formatter.format(PlainDate.of(1712, 3, 11)),
            is("30. februari 1712"));
    }

    @Test
    public void parseSweden2() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("sv"))
                .addPattern("d. MMMM yyyy", PatternType.CLDR)
                .build()
                .with(ChronoHistory.ofSweden())
                .withDefault(ChronoHistory.ofSweden().era(), HistoricEra.AD);
        assertThat(
            formatter.parse("30. februari 1712"),
            is(PlainDate.of(1712, 3, 11)));
    }

    @Test
    public void printRedOctober() {
        Locale russia = new Locale("en", "RU");
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, russia)
                .addPattern("d. MMMM yyyy", PatternType.CLDR)
                .build()
                .withGregorianCutOver(ChronoHistory.of(russia).getGregorianCutOverDate());
        assertThat(
            formatter.format(PlainDate.of(1917, 11, 7)),
            is("25. October 1917"));
    }

    @Test
    public void parseRedOctober1() throws ParseException {
        Locale russia = new Locale("en", "RU");
        ChronoHistory history = ChronoHistory.of(russia);
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, russia)
                .addPattern("d. MMMM yyyy", PatternType.CLDR)
                .build()
                .withGregorianCutOver(history.getGregorianCutOverDate())
                .withDefault(history.era(), HistoricEra.AD);
        assertThat(
            formatter.parse("25. October 1917"),
            is(PlainDate.of(1917, 11, 7)));
    }

    @Test
    public void parseRedOctober2() throws ParseException {
        Locale russia = new Locale("en", "RU");
        ChronoHistory history = ChronoHistory.of(russia);
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, russia)
                .addInteger(history.dayOfMonth(), 1, 2)
                .addLiteral(". ")
                .addText(history.month())
                .addLiteral(' ')
                .addFixedInteger(history.yearOfEra(), 4)
                .build()
                .withDefault(history.era(), HistoricEra.AD);
        assertThat(
            formatter.parse("25. October 1917"),
            is(PlainDate.of(1917, 11, 7)));
    }

    @Test
    public void printRussiaByzantine1() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("en", "RU"))
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        PlainDate ad1522 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 1522, 8, 31));
        assertThat(
            formatter.format(ad1522),
            is("31. August Anno Mundi 7030/31"));
        assertThat(
            formatter.format(ad1522.plus(1, CalendarUnit.DAYS)),
            is("1. September Anno Mundi 7031"));
    }

    @Test
    public void parseRussiaByzantine1() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, new Locale("en", "RU"))
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT);
        PlainDate ad1522 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 1522, 8, 31));
        assertThat(
            formatter.parse("31. August Anno Mundi 7030/31"),
            is(ad1522));
        assertThat(
            formatter.parse("31. August Anno Mundi 7031"),
            is(ad1522));
        assertThat(
            formatter.parse("1. September Anno Mundi 7031"),
            is(ad1522.plus(1, CalendarUnit.DAYS)));
    }

    @Test
    public void printRussiaByzantine2() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.ENGLISH)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT)
                .with(ChronoHistory.PROLEPTIC_BYZANTINE);
        PlainDate ad2015 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 2015, 8, 31));
        assertThat(
            formatter.format(ad2015),
            is("31. August Anno Mundi 7523/24"));
        assertThat(
            formatter.format(ad2015.plus(1, CalendarUnit.DAYS)),
            is("1. September Anno Mundi 7524"));
    }

    @Test
    public void parseRussiaByzantine2() throws ParseException {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.ENGLISH)
                .addPattern("d. MMMM GGGG yyyy", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT)
                .with(ChronoHistory.PROLEPTIC_BYZANTINE);
        PlainDate ad2015 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 2015, 8, 31));
        assertThat(
            formatter.parse("31. August Anno Mundi 7523/24"),
            is(ad2015));
        assertThat(
            formatter.parse("31. August Anno Mundi 7524"),
            is(ad2015));
        assertThat(
            formatter.parse("1. September Anno Mundi 7524"),
            is(ad2015.plus(1, CalendarUnit.DAYS)));
    }

    @Test
    public void printAUC() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.US)
                .addPattern("MM/dd/y G", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT)
                .with(ChronoHistory.ofFirstGregorianReform().with(EraPreference.abUrbeCondita()));
        PlainDate ad1 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.AD, 1, 1, 1));
        assertThat(
            formatter.format(ad1),
            is("01/01/754 a.u.c."));
    }

    @Test
    public void parseAUC() {
        ChronoFormatter<PlainDate> formatter =
            ChronoFormatter.setUp(PlainDate.class, Locale.US)
                .addPattern("MM/dd/", PatternType.CLDR)
                .addInteger(PlainDate.YEAR, 2, 4) // test for min-width of 2 digits
                .addPattern(" G", PatternType.CLDR)
                .build()
                .with(Leniency.STRICT)
                .with(ChronoHistory.ofFirstGregorianReform().with(EraPreference.abUrbeCondita()));
        PlainDate ad1 = ChronoHistory.PROLEPTIC_JULIAN.convert(HistoricDate.of(HistoricEra.BC, 753, 1, 1));
        assertThat(
            formatter.format(ad1),
            is("01/01/01 a.u.c."));
    }

}