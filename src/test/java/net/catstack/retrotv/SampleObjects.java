package net.catstack.retrotv;

import net.catstack.retrotv.entity.heading.HeadingModel;

public class SampleObjects {
    public static HeadingModel getHeadingModel() {
        var headingModel = new HeadingModel();

        headingModel.setColor(13);
        headingModel.setName("Test Heading");

        return headingModel;
    }
}
