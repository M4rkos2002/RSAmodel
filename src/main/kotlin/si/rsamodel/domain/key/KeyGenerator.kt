package si.rsamodel.domain.key

import si.rsamodel.domain.model.Key

interface KeyGenerator<P, S> {
    public fun generateKey(): Key<P,S>
}