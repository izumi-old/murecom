package org.izumi.murecom;

import io.jmix.core.Id;
import io.jmix.ui.util.OperationResult;
import org.izumi.murecom.entity.User;

public interface RulesLogic {
    OperationResult proceed(Id<User> userId);
}
