package aol_codere;

public class ShotOutcome {
    public enum Type { HIT, SUNK, MISS, ALREADY_HIT }

    final Type type;
    final int sunkShipLength;

    private ShotOutcome(Type type, int sunkShipLength) {
        this.type = type;
        this.sunkShipLength = sunkShipLength;
    }

    static ShotOutcome hit() {
        return new ShotOutcome(Type.HIT, -1);
    }

    static ShotOutcome sunk(int length) {
        return new ShotOutcome(Type.SUNK, length);
    }

    static ShotOutcome miss() {
        return new ShotOutcome(Type.MISS, -1);
    }

    static ShotOutcome alreadyHit() {
        return new ShotOutcome(Type.ALREADY_HIT, -1);
    }
}
