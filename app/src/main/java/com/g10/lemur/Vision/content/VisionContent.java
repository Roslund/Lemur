package com.g10.lemur.Vision.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.g10.lemur.Vision.Vision.imageColors;
import static com.g10.lemur.Vision.Vision.imageLabels;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class VisionContent
{

    //private static final int COUNT = 10;


    /**
     * An array of sample (dummy) items.
     */
    public static final List<VisionItem> ITEMS = new ArrayList<VisionItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, VisionItem> ITEM_MAP = new HashMap<String, VisionItem>();



    static
    {
        // Add some sample items.
        addItem(createVisionItem("Labels", imageLabels, "Labels"));
        addItem(createVisionItem("Safe Search", "Super Safe", "Safe"));
        addItem(createVisionItem("Colors", imageColors, "Colors"));
    }

    private static void addItem(VisionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static VisionItem createVisionItem(String title, String content, String type) {
        int position = ITEMS.size() +1;
        return new VisionItem(String.valueOf(position), title, content, type);
    }

    private static String makeDetails(int position) {

        return "Here goes the details for card #"+position;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class VisionItem {
        public final String id;
        public final String title;
        public final String content;
        public final String type;

        public VisionItem(String id, String title, String content, String type) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.type = type;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
