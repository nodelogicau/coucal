/*
 *  Copyright 2023 Ben Fortuna
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.coucal.command;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Base class for all commands that will invoke the specified consumer upon execution completion.
 * @param <T> the command result type
 */
public abstract class AbstractCommand<T> implements Callable<Integer> {

    private Consumer<T> consumer;

    public AbstractCommand() {
        this.consumer = DefaultOutputHandlers.STDOUT_PRINTER();
    }

    public AbstractCommand(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public AbstractCommand<T> withConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public final Consumer<T> getConsumer() {
        return consumer;
    }
}
