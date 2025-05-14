package uno;

public class NumberedCard extends ColoredCard {
    private int number;

    public NumberedCard(String color, int number) {
        super();
        this.color = color;
        this.number = number;
        this.type = "Numbered";
    }
    public int getNumber() {
        return number;
    }
    public boolean accepts(Card aCard) {

        boolean isSameColor = aCard.colorIsValid(this);

        if (aCard.type.equals(type)) {
            return aCard.getNumber() == number || isSameColor;
        }

        return isSameColor;
    }





}
