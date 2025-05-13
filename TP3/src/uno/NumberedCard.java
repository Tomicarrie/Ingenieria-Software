package uno;

public class NumberedCard extends Card {
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
    public boolean isValid(Card aCard) {
        
        boolean isSameColor = colorIsValid(aCard);
        if (aCard.type.equals(type)) {
            return aCard.getNumber() == number || isSameColor;
        }
        return isSameColor;

    }

}
