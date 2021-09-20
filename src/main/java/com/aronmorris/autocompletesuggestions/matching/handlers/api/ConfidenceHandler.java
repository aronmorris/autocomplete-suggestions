package com.aronmorris.autocompletesuggestions.matching.handlers.api;

public interface ConfidenceHandler<I, O> {

    double NO_CONFIDENCE = 0.0;

    O process(I additional, O modifiable);

    void modifyWeight(double newWeight);
}
