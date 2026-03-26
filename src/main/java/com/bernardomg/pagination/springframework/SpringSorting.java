/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.pagination.springframework;

import java.util.Collection;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

/**
 * Transforms a {@link Sorting} into a Spring {@link Sort} and back.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class SpringSorting {

    /**
     * Transforms a {@link Property} into a {@link Order}.
     *
     * @param property
     *            {@code Property} to transform
     * @return a {@code Property} created from the {@code Order}
     */
    public static final Order toOrder(final Property property) {
        final Order order;

        if (Direction.ASC.equals(property.direction())) {
            order = Order.asc(property.name());
        } else {
            order = Order.desc(property.name());
        }

        return order;
    }

    /**
     * Transforms a {@link Order} into a {@link Property}.
     *
     * @param order
     *            {@code Order} to transform
     * @return a {@code Property} created from the {@code Order}
     */
    public static final Property toProperty(final Order order) {
        final Direction direction;

        if (order.isAscending()) {
            direction = Direction.ASC;
        } else {
            direction = Direction.DESC;
        }
        return new Property(order.getProperty(), direction);
    }

    /**
     * Transforms a {@link Sorting} into a {@link Sort}.
     *
     * @param sorting
     *            {@code Sorting} to transform
     * @return a {@code Sort} created from the {@code Sorting}
     */
    public static final Sort toSort(final Sorting sorting) {
        return Sort.by(sorting.properties()
            .stream()
            .map(SpringSorting::toOrder)
            .toList());
    }

    /**
     * Transforms a {@link Sort} into a {@link Sorting}.
     *
     * @param sort
     *            {@code Sort} to transform
     * @return a {@code Sorting} created from the {@code Sort}
     */
    public static final Sorting toSorting(final Sort sort) {
        final Collection<Property> properties;

        properties = sort.stream()
            .map(SpringSorting::toProperty)
            .toList();

        return new Sorting(properties);
    }

    /**
     * Private constructor.
     */
    private SpringSorting() {
        super();
    }

}
