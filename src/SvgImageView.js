import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { requireNativeComponent, View, ViewPropTypes, ImageProperties } from 'react-native';

class SvgImageView extends Component {
  render() {
    const { src, tintColor, style } = this.props;
    
    return (
      <NativeSvgImageView
        src={src}
        style={style}
        tintColor={tintColor}
      />
    );
  }
}

SvgImageView.propTypes = {
  ...(ViewPropTypes || View.propTypes || ImageProperties),
  src: PropTypes.string,
  tintColor: PropTypes.string,
};

const NativeSvgImageView = requireNativeComponent('SvgImageView', SvgImageView);

module.exports = SvgImageView;