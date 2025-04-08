package anillo;

public abstract class Link {
    public Object cargo;
    public Link next_link;
    public Link prev_link;
    
    public abstract Link next();
    public abstract Link add( Object cargo );
    public abstract Object current();
    public abstract Link remove();
}
