package guru.sfg.beer.inventory.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(ActiveMQTextMessage message) throws IOException {


        NewInventoryEvent newInventoryEvent = objectMapper.readValue(message.getText(), NewInventoryEvent.class);

        BeerDto beerDto = newInventoryEvent.getBeerDto();

        log.info("Inventory request just arrived: " + beerDto.toString());


        beerInventoryRepository.findByUpc(beerDto.getUpc()).ifPresentOrElse(beerInventory -> {

            log.info("Updating beer inventory: " + beerInventory.toString());

            beerInventory.setQuantityOnHand(beerDto.getQuantityOnHand());
            beerInventoryRepository.save(beerInventory);

        }, () -> log.info("not found: " + beerDto.getUpc()));

    }

}
