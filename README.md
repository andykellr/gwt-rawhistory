gwt-rawhistory
==============

Alternate implementation of GWT History that doesn't encode/decode the history token (hash)

We experienced many issues with the encoding of the History token and decided to write an alternate implementation that didn't attempt to encode/decode. Instead, our other code that sets and interprets the history token handles the encoding and decoding. This results in more consistent and predictable behavior across browsers.

This demo demonstrates the change in behavior: [History](http://andykellr.github.io/gwt-rawhistory/com.tractionsoftware.gwt.demo.history.HistoryDemo/HistoryDemo.html) vs [RawHistory](http://andykellr.github.io/gwt-rawhistory/com.tractionsoftware.gwt.demo.history.RawHistoryDemo/RawHistoryDemo.html)
