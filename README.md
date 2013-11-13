gwt-rawhistory
==============

Alternate implementation of GWT History that doesn't encode/decode the history token (hash)

We experienced many issues with the encoding of the History token and decided to write an alternate implementation that didn't attempt to encode/decode. Instead, our other code that sets and interprets the history token handles the encoding and decoding. This results in more consistent and predictable behavior across browsers.

## Demo

This demo demonstrates the change in behavior: [History](http://andykellr.github.io/gwt-rawhistory/com.tractionsoftware.gwt.demo.history.HistoryDemo/HistoryDemo.html) vs [RawHistory](http://andykellr.github.io/gwt-rawhistory/com.tractionsoftware.gwt.demo.history.RawHistoryDemo/RawHistoryDemo.html)

## Building

Run ant with -Dgwt.home= or set the GWT_HOME environment variable.

`ant` will compile the source and create build/dist/gwt-rawhistory.jar

`ant dist` will also build the demos and create build/dist/gwt-rawhistory.zip containing the .jar file

`ant clean` will delete the build directory

## Installing

Put the gwt-rawhistory.jar file in your classpath and include RawHistory in your .gwt.xml file.

```xml
<inherits name='com.tractionsoftware.gwt.history.RawHistory'/>
```

## Contributing

Pull requests are welcome.

## Notes

If you look at the commit history, you'll see that I started with the existing GWT HistoryImpl and made changes from there. Most of the changes were package, name, and JSNI references to bind to the new package and name. The meaningful changes are as follows:

In RawHistoryImpl:

```java
  /**
   * No decoding for RawHistory.
   */
  @Override
  protected String decodeFragment(String encodedFragment) {
    return encodedFragment;
  }

  /**
   * No encoding for RawHistory.
   */
  @Override
  public String encodeFragment(String fragment) {
    return fragment;
  }
    
  /**
   * Some browsers either don't provide or prematurely decode
   * window.location.hash, so we don't use it.
   */
  private static native String getLocationHash() 
  /*-{
    var href = $wnd.location.href;
    var hashIndex = href.lastIndexOf("#");
    return (hashIndex > 0) ? href.substring(hashIndex) : "";
  }-*/;
```

We use getLocationHash() in places where $wnd.location.hash was used to address browsers that attempt to decode it.

We also removed the gecko1_8 implementation from the .gwt.xml because the timer isn't necessary for recent Firefox and it was causing issues with IE11.


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/andykellr/gwt-rawhistory/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

