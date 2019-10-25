package guru.sfg.beer.inventory.service.events;

import guru.sfg.beer.inventory.service.web.model.BeerDto;


public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent() {

    }

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
