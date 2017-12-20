package com.gooalgene.primer.interfaces;

import com.gooalgene.primer.bean.Primer;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyan on 2017/12/15.
 */
public interface Primer3Service {

    Map<Integer,List<Primer>> getPrimer(String url, String param);
}
