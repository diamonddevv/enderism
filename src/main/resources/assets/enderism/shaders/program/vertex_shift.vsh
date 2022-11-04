#version 150

in vec4 Position;

uniform mat4 ProjMat;
uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec2 ScreenSize;

uniform float Strength;

out vec2 texCoord;
out vec2 oneTexel;

float invert(float f, float amount) {
    float amt = clamp(amount, 0.0, 1.0);
    float bit = f / 100;
    return f - (bit * amt);
}

void main() {
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    oneTexel = 1.0 / InSize;
    texCoord = Position.xy / OutSize;

    vec2 center = vec2(0.5, 0.5);
    float diff = length(center - outPos.xy);

    outPos.z = outPos.z - (diff * Strength);
    outPos = ProjMat * outPos;
    gl_Position = outPos;
}
