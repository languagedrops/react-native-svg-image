import * as React from 'react';
import { ViewStyle } from 'react-native';

export interface SvgImageViewProperties {
    src: string,
    style?: ViewStyle;
}

export class SvgImageView extends React.Component<SvgImageViewProperties, {}> {}