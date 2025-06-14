package org.udesa.unoback.model;

import java.util.Objects;


public class NumberCard extends ColoredCard {

    private int number;

    public int number() {
        return number;
    }

    public static Card asCard( JsonCard aJson ) {
        return new NumberCard( aJson.getColor(), aJson.getNumber() ).shoutAs( aJson.isShout() );
    }

    public NumberCard( String aColor, int aNumber ) {
        super( aColor );
        if ( aNumber < 0 || aNumber > 9 ) {
            throw new IllegalArgumentException( "Number must be between 0 and 9" );
        }
        number = aNumber;
    }

    public boolean acceptsOnTop( Card aCard ) { return super.acceptsOnTop( aCard ) || aCard.yourNumberIs( number );}
    public boolean yourNumberIs( int number ) { return number == this.number;   }

    public boolean equals( Object o ) { return super.equals( o ) && number == ((NumberCard) o).number; }
    public int hashCode() {             return Objects.hash( super.hashCode(), number );}

    public JsonCard asJson() { return new JsonCard( color, number, getClass().getSimpleName(), unoShouted() ); }

}
