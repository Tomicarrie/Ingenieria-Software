package org.udesa.unoback.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class ColoredCard extends Card {
    protected Set<String> validColors = Set.of("Red", "Blue", "Green", "Yellow");
    protected String color = "";

    public ColoredCard( String aColor ) {
        if ( validColors.contains(aColor) ) {
            color = aColor;
        } else {
            throw new IllegalArgumentException("Invalid color");
        }
    }
    public boolean acceptsOnTop( Card aCard ) { return  aCard.yourColorIs( color() );   }
    public boolean yourColorIs( String aColor ) { return color.equals( aColor );  }
    public String color() { return color;  }

    public boolean equals( Object o ) { return super.equals( o ) && color.equals( ColoredCard.class.cast( o ).color );  }
    public int hashCode() {             return Objects.hash( color );}

    public JsonCard asJson() { return new JsonCard( color, null, getClass().getSimpleName(), unoShouted() ); }
}
