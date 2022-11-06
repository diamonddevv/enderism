#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec3 ColorA;
uniform vec3 ColorB;
uniform vec3 ColorC;

uniform float Intensity;
uniform float STime;
uniform float SLevel;

out vec4 fragColor;

vec3 conditionedTrilerp(float condition, vec3 uno, vec3 dos, vec3 tres, float v) {
    // do something i guess
}

vec4 overlay(vec4 ogC, vec3 inC, float intensity) {
    return vec4(ogC.rgb + (inC * intensity),ogC.a);
}

void main() {
    vec4 InTexel = texture(DiffuseSampler, texCoord);
    float lvl = SLevel + 1;
    float tlerpVal = lvl * sin(STime);
    vec3 col = conditionedTrilerp(cos(STime), ColorA, ColorB, ColorC, tlerpVal);
    fragColor = overlay(InTexel, col, Intensity);
}
