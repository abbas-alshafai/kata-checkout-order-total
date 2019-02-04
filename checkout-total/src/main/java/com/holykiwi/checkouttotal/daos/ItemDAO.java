package com.holykiwi.checkouttotal.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.holykiwi.checkouttotal.dtos.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ItemDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDAO.class);
    private Map<String, ItemDTO> items = new HashMap<>();

    public String save(ItemDTO item)
    {
        if(items.containsKey(item.getName()))
            LOGGER.warn("The item you try to add already exists and it it will be overridden by the new one. " +
                    "Printing the old item:%n{}", items.containsKey(item.getName()));

        LOGGER.debug("Adding new item to the repository:%n{}", item.toString());
        items.put(item.getName(), item);

        /*
        In this case it might not make sense to call the map to get the name we just stored (included in the dto).
        However it is good practice to get it from the repository to confirm storing it. This would make more sense
        if the repository generates IDs.
         */
        return items.get(item.getName()).getName();
    }

    public ItemDTO findByName(String itemName)
    {
        LOGGER.debug("find item by name:\t{}", itemName);
        return items.get(itemName);
    }
}
