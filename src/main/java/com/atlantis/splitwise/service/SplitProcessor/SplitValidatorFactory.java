package com.atlantis.splitwise.service.SplitProcessor;

import com.atlantis.splitwise.utils.enums.SplitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SplitValidatorFactory {

    @Value("${default.split-type}")
    private SplitType defaultSplitValidator;

    @Autowired
    private List<SplitProcessor> splitProcessors;

    private static final Map<SplitType, SplitProcessor> splitValidatorCache = new HashMap<>();

    @PostConstruct
    public void initSplitValidatorCache() {
        splitProcessors.forEach(splitProcessor -> splitValidatorCache.put(splitProcessor.getSplitType(), splitProcessor));
    }

    public SplitProcessor getSplitValidator(SplitType splitType){
        return splitValidatorCache.getOrDefault(splitType, splitValidatorCache.get(defaultSplitValidator));
    }
}
