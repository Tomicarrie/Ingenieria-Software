package anillo;
import java.util.ArrayList;


public class Ring {

    private ArrayList<Object> anillo = new ArrayList<Object>();
    private Integer curr = (-1);

    public Ring next() {
        try {
            this.curr = (this.curr + 1) % this.anillo.size();
            return this;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Object current() {
        try {
            return this.anillo.get(this.curr);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Ring add( Object cargo ) {

        ArrayList<Object> newRing = new ArrayList<Object>();
        newRing.add(cargo);
        int size = this.anillo.size();

        for (int i = 0; i < size; i++) {
            newRing.add(this.anillo.get((i + this.curr) % size));
        }

        this.curr = 0;
        this.anillo = newRing;
        return this;

    }

    public Ring remove() {
        ArrayList<Object> newRing = new ArrayList<Object>();
        int size = this.anillo.size();

        for (int i = 0; i < size - 1; i++) {
            newRing.add(this.anillo.get((i + this.curr + 1) % size));
        }

        this.curr = 0;
        this.anillo = newRing;
        return this;
    }
}
