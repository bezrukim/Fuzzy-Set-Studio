
<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  modelValue: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['update:modelValue'])

function updatePoint(index, field, value) {
  const points = props.modelValue.map(point => ({ ...point }))
  points[index][field] = value === '' ? '' : Number(value)

  emit('update:modelValue', points)
}

function addPoint() {
  const points = [...props.modelValue]
  const lastX = points.length
      ? Number(points[points.length - 1].x)
      : 0

  points.push({
    x: Number.isFinite(lastX) ? lastX + 1 : 0,
    y: 0
  })

  emit('update:modelValue', points)
}

function removePoint(index) {
  const points = props.modelValue.filter((_, i) => i !== index)
  emit('update:modelValue', points)
}
</script>

<template>
  <div class="card h-100">
    <div class="card-header d-flex justify-content-between align-items-center">
      <strong>{{ title }}</strong>
      <button class="btn btn-sm btn-outline-primary" @click="addPoint">
        + Точка
      </button>
    </div>

    <div class="card-body">
      <div
          v-for="(point, index) in modelValue"
          :key="index"
          class="row g-2 align-items-center mb-2"
      >
        <div class="col-5">
          <label class="form-label small">x</label>
          <input
              type="number"
              step="any"
              class="form-control"
              :value="point.x"
              @input="updatePoint(index, 'x', $event.target.value)"
          >
        </div>

        <div class="col-5">
          <label class="form-label small">μ(x)</label>
          <input
              type="number"
              min="0"
              max="1"
              step="any"
              class="form-control"
              :value="point.y"
              @input="updatePoint(index, 'y', $event.target.value)"
          >
        </div>

        <div class="col-2 d-flex align-items-end">
          <button
              class="btn btn-outline-danger btn-sm w-100"
              :disabled="modelValue.length <= 1"
              @click="removePoint(index)"
              title="Удалить точку"
          >
            ×
          </button>
        </div>
      </div>

      <div class="text-muted small mt-3">
        Координаты x задают положение точки,
        μ(x) — степень принадлежности от 0 до 1.
      </div>
    </div>
  </div>
</template>
