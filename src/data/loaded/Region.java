package data.loaded;

public enum Region {
    PAPERKIND,
    DUSGAR,
    MIRRIMAM,
    BAZAAR_BELOW,
    YACKRIX,
    SHATTERED_ISLAND,
    UNDERIX,
    ORTERIX,
    ASCENSIA,
    BAKTIX,
    WORTSMAR,
    KROAKA,
    TRALARRY,
    COAR,
    GRANVIDAS,
    FLAMKA;

    public static Region fromString(String nameString) {
        String name = nameString.toLowerCase();
        if (name.startsWith(PAPERKIND.name().toLowerCase())) {
            return PAPERKIND;
        } else if (name.equals(DUSGAR.name().toLowerCase())) {
            return DUSGAR;
        } else if (name.equals(MIRRIMAM.name().toLowerCase())) {
            return MIRRIMAM;
        } else if (name.equals("bazaar below")) {
            return BAZAAR_BELOW;
        } else if (name.equals(YACKRIX.name().toLowerCase())) {
            return YACKRIX;
        } else if (name.equals("shattered island")) {
            return SHATTERED_ISLAND;
        } else if (name.equals(UNDERIX.name().toLowerCase())) {
            return UNDERIX;
        } else if (name.equals(ORTERIX.name().toLowerCase())) {
            return ORTERIX;
        } else if (name.equals(ASCENSIA.name().toLowerCase())) {
            return ASCENSIA;
        } else if (name.equals(BAKTIX.name().toLowerCase())) {
            return BAKTIX;
        } else if (name.equals(WORTSMAR.name().toLowerCase())) {
            return WORTSMAR;
        } else if (name.equals(KROAKA.name().toLowerCase())) {
            return KROAKA;
        } else if (name.equals(TRALARRY.name().toLowerCase())) {
            return TRALARRY;
        } else if (name.equals(COAR.name().toLowerCase())) {
            return COAR;
        } else if (name.equals(GRANVIDAS.name().toLowerCase())) {
            return GRANVIDAS;
        } else if (name.equals(FLAMKA.name().toLowerCase())) {
            return FLAMKA;
        }
        throw new IllegalArgumentException("Region string not recognized!");
    }
    public static Region fromCharacter(char c) {
        return switch (c) {
            case 'P' -> PAPERKIND;
            case 'D' -> DUSGAR;
            case 'M' -> MIRRIMAM;
            case 'Z' -> BAZAAR_BELOW;
            case 'Y' -> YACKRIX;
            case 'S' -> SHATTERED_ISLAND;
            case 'U' -> UNDERIX;
            case 'O' -> ORTERIX;
            case 'A' -> ASCENSIA;
            case 'B' -> BAKTIX;
            case 'W' -> WORTSMAR;
            case 'K' -> KROAKA;
            case 'T' -> TRALARRY;
            case 'C' -> COAR;
            case 'G' -> GRANVIDAS;
            case 'F' -> FLAMKA;
            default -> null;
        };
    }
}
