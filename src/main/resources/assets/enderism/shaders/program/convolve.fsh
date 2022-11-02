#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec4 Color;
uniform float STime;
uniform float Intensity;


out vec4 fragColor;

vec4 colorMix(vec4 ogC, vec4 inC, float intensity) {
    return ogC + ((ogC - inC) * intensity);
}


void main() {
    vec4 InTexel = texture(DiffuseSampler, texCoord);
    fragColor = colorMix(InTexel, Color, Intensity * sin(STime));
}
