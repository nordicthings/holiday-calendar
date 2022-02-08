package org.nordicthings.commons.holiday.german;

import org.nordicthings.commons.holiday.AdministrativeArea;

/*
Kürzel der Bundesländer gemäß ISO 31622-2
Siehe https://de.wikipedia.org/wiki/ISO_3166-2:DE
 */
public enum GermanFederalState implements AdministrativeArea {

    BADEN_WUERTTEMBERG("DE-BW", "Baden-Württemberg"),
    BAYERN("DE-BY", "Bayern"),
    BERLIN("DE-BE", "Berlin"),
    BRANDENBURG("DE-BB", "Brandenburg"),
    BREMEN("DE-HB", "Bremen"),
    HAMBURG("DE-HH", "Hamburg"),
    HESSEN("DE-HE", "Hessen"),
    MECKLENBURG_VORPOMMERN("MV", "Mecklenburg-Vorpommern"),
    NIEDERSACHSEN("DE-NI", "Niedersachsen"),
    NORDRHEIN_WESTFALEN("DE-NW", "Nordrhein-Westfalen"),
    RHEINLAND_PFALZ("DE-RP", "Rheinland-Pfalz"),
    SAARLAND("DE-SL", "Saarland"),
    SACHSEN("DE-SN", "Sachsen"),
    SACHSEN_ANHALT("DE-ST", "Sachsen-Anhalt"),
    SCHLESWIG_HOLSTEIN("DE-SH", "Schleswig-Holstein"),
    THUERINGEN("DE-TH", "Thüringen");

    private final String iso;
    private final String name;

    GermanFederalState(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public String getISO() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public static GermanFederalState getByISO(String iso) {
        for (GermanFederalState state : values()) {
            if (state.getISO().equals(iso)) {
                return state;
            }
        }
        return null;
    }

}
