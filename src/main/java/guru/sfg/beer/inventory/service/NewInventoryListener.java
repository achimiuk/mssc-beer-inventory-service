package guru.sfg.beer.inventory.service;

import guru.sfg.beer.common.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NewInventoryListener {

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void onNewInventoryEvent(NewInventoryEvent newInventoryEvent) {
        log.debug("New inventory QOH: " + newInventoryEvent.getBeerDto().getQuantityOnHand());
    }

}
