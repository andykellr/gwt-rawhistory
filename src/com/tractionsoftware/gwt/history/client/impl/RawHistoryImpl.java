/*
 * Based on code from GWT HistoryImpl, Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tractionsoftware.gwt.history.client.impl;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Native implementation associated with
 * {@link com.google.gwt.user.client.History}. User classes should not use this
 * class directly.
 * 
 * <p>
 * This base version uses the HTML5 standard window.onhashchange event to
 * determine when the URL hash identifier changes.
 * </p>
 */
public class RawHistoryImpl extends com.google.gwt.user.client.impl.HistoryImpl {

  private JavaScriptObject oldHandler;

  public native void dispose() /*-{
    $wnd.onhashchange = this.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::oldHandler;
  }-*/;

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

  @Override
  public native boolean init() /*-{
    var token = '';

    // Get the initial token from the url's hash component.
    var hash = @com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::getLocationHash()();
    if (hash.length > 0) {
      token = this.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::decodeFragment(Ljava/lang/String;)(hash.substring(1));
    }

    @com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::setToken(Ljava/lang/String;)(token);

    var historyImpl = this;

    historyImpl.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::oldHandler = $wnd.onhashchange;

    $wnd.onhashchange = $entry(function() {
      var token = '', hash = @com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::getLocationHash()();
      if (hash.length > 0) {
        token = historyImpl.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::decodeFragment(Ljava/lang/String;)(hash.substring(1));
      }

      historyImpl.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::newItemOnEvent(Ljava/lang/String;)(token);
      var oldHandler = historyImpl.@com.tractionsoftware.gwt.history.client.impl.RawHistoryImpl::oldHandler;
      if (oldHandler) {
        oldHandler();
      }
    });

    return true;
  }-*/;

  /**
   * The standard updateHash implementation assigns to location.hash() with an
   * encoded history token.
   */
  @Override
  protected native void nativeUpdate(String historyToken) /*-{
    $wnd.location.hash = this.@com.google.gwt.user.client.impl.HistoryImpl::encodeFragment(Ljava/lang/String;)(historyToken);
  }-*/;

  @Override
  protected void nativeUpdateOnEvent(String historyToken) {
    // Do nothing, the hash is already updated.
  }
}
