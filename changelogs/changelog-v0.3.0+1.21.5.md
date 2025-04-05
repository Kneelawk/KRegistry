Changes:

* Turned `KRegistrar` and `KHolder` into interfaces.
* The old `KRegistrar` became a final class `KCoreRegistrar` which specialized `KRegistrar` implementations wrap around.
* The old `KHolder` became a `KHolderBase` which specialized implementations extend.
* Added specialized implementations for items and blocks.
