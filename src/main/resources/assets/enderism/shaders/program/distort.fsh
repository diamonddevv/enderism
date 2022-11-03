#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform float STime;
uniform float SLevel;
uniform float Strength;

out vec4 fragColor;

float invert(float f, float amount) {
    float amt = clamp(amount, 0.0, 1.0);
    float bit = f / 100;
    return f - (bit * amt);
}
vec2 invert(vec2 vec, float amount) {
    return vec2(invert(vec.x, amount), invert(vec.y, amount));
}

vec2 distort(vec2 p, float intensity) { // taken from <https://www.shadertoy.com/view/wtBXRz>, edited
    p = p * 2.0 - 1.0; // Pos intensity: Barrel - Neg intensity: Pincushion

    float r2 = p.x * p.x + p.y * p.y;
    p *= 1.0 + intensity * r2 * r2 * r2;

    p = (p * .5 + .5);
    return p;
}

void main(){
    vec4 InTexel = texture(DiffuseSampler, texCoord);
    vec2 uv = texCoord.xy / InSize.xy;
    uv = invert(distort(texCoord, (SLevel * Strength)), sin(STime));
    fragColor = vec4(inTexel, uv, 1.);

}
