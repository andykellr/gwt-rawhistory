gwt-rawhistory
==============

Alternate implementation of GWT History that doesn't encode/decode the history token (hash)

We experienced many issues with the encoding of the History token and decided to write an alternate implementation that didn't attempt to encode/decode. Instead, our other code that sets and interprets the history token handles the encoding and decoding. This results in more consistent and predictable behavior across browsers.

This deserves more explanation and I will add some examples soon.
