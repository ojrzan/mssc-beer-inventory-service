package guru.sfg.beer.inventory.service.events;


import guru.sfg.beer.inventory.service.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {


    public BrewBeerEvent() {

    }

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
