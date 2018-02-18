package ru.alexsuvorov.paistewiki.ItemModel;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;

public class NewsHeaderItem extends NewsModel implements StickyHeader {

    public NewsHeaderItem(String Title, String URL) {
        super(Title, URL);
    }
}

