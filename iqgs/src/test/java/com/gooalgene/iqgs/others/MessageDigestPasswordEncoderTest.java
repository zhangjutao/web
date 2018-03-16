package com.gooalgene.iqgs.others;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class MessageDigestPasswordEncoderTest extends TestCase {

    @Test
    public void testEncodePassUsingMD5(){
        MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
        String encodeResult = encoder.encodePassword("gooalgene", null);
        assertEquals("158b8e42415a522d9b991ea3d5899dff", encodeResult);
    }
}
