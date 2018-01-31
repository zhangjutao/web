package com.gooalgene.iqgs.others;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class MessageDigestPasswordEncoderTest extends TestCase {

    @Test
    public void testEncodePassUsingMD5(){
        MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
        String encodeResult = encoder.encodePassword("123456", null);
        assertEquals("e10adc3949ba59abbe56e057f20f883e", encodeResult);
    }
}
