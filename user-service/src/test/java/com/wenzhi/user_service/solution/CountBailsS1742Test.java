package com.wenzhi.user_service.solution;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountBailsS1742Test {

    private static final Logger log = LoggerFactory.getLogger(CountBailsS1742Test.class);

    @Test
    void countBails() {
        int lowLimit = 1;
        int highLimit = 10;
        CountBailsS1742 c = new CountBailsS1742();
        int actual = c.countBails(lowLimit, highLimit);
        log.info("运行结果: {}", actual);
        assertEquals(2, actual);
    }
}