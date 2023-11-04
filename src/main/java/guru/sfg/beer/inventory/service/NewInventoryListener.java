package guru.sfg.beer.inventory.service;

import guru.sfg.beer.common.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void onNewInventoryEvent(NewInventoryEvent newInventoryEvent) {
        log.debug("New inventory requested QOH: " + newInventoryEvent.getBeerDto().getQuantityOnHand());

        BeerInventory beerInventory = beerInventoryRepository.findByUpc(newInventoryEvent.getBeerDto().getUpc());

        beerInventory.setQuantityOnHand(beerInventory.getQuantityOnHand() + newInventoryEvent.getBeerDto().getQuantityOnHand());

        beerInventoryRepository.save(beerInventory);

    }

}
