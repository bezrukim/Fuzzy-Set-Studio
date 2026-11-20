<script setup>
import { toRaw } from 'vue'
const props = defineProps({
  title: {
    type: String,
    default: 'Отношение'
  },
  modelValue: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['update:modelValue'])

function updateAxis(axis, index, value) {
  const relation = structuredClone(toRaw(props.modelValue))
  relation[axis][index] = value === '' ? '' : Number(value)
  emit('update:modelValue', relation)
}

function updateCell(row, column, value) {
  const relation = structuredClone(toRaw(props.modelValue))
  relation.R[row][column] =
      value === '' ? '' : Number(value)

  emit('update:modelValue', relation)
}

function addX() {
  console.log('addX вызвана')

  const relation = structuredClone(toRaw(props.modelValue))

  console.log('До изменения:', relation)

  const lastX = relation.x.length
      ? Number(relation.x[relation.x.length - 1])
      : 0

  relation.x.push(Number.isFinite(lastX) ? lastX + 1 : 0)
  relation.R.push(Array(relation.y.length).fill(0))

  console.log('После изменения:', relation)

  emit('update:modelValue', relation)
}

function removeX(index) {
  const relation = structuredClone(toRaw(props.modelValue))

  if (relation.x.length <= 1) return

  relation.x.splice(index, 1)
  relation.R.splice(index, 1)

  emit('update:modelValue', relation)
}

function addY() {
  const relation = structuredClone(toRaw(props.modelValue))

  const lastY = relation.y.length
      ? Number(relation.y[relation.y.length - 1])
      : 0

  relation.y.push(Number.isFinite(lastY) ? lastY + 1 : 0)

  relation.R.forEach(row => row.push(0))

  emit('update:modelValue', relation)
}

function removeY(index) {
  const relation = structuredClone(toRaw(props.modelValue))

  if (relation.y.length <= 1) return

  relation.y.splice(index, 1)

  relation.R.forEach(row => {
    row.splice(index, 1)
  })

  emit('update:modelValue', relation)
}
</script>

<template>
  <div class="card h-100">
    <div class="card-header d-flex justify-content-between align-items-center">
      <strong>{{ title }}</strong>

      <div class="btn-group btn-group-sm">
        <button type="button" class="btn btn-outline-primary" @click="addX">
          + X
        </button>

        <button class="btn btn-outline-primary" @click="addY">
          + Y
        </button>
      </div>
    </div>

    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-bordered text-center align-middle relation-table">
          <thead>
          <tr>
            <th class="axis-cell">X \ Y</th>

            <th v-for="(y, j) in modelValue.y" :key="j">
              <div class="d-flex flex-column gap-1">
                <input
                    type="number"
                    step="any"
                    class="form-control form-control-sm text-center"
                    :value="y"
                    @input="updateAxis('y', j, $event.target.value)"
                >

                <button
                    class="btn btn-outline-danger btn-sm"
                    :disabled="modelValue.y.length <= 1"
                    @click="removeY(j)"
                    title="Удалить столбец"
                >
                  ×
                </button>
              </div>
            </th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="(x, i) in modelValue.x" :key="i">
            <th>
              <div class="d-flex flex-column gap-1">
                <input
                    type="number"
                    step="any"
                    class="form-control form-control-sm text-center"
                    :value="x"
                    @input="updateAxis('x', i, $event.target.value)"
                >

                <button
                    class="btn btn-outline-danger btn-sm"
                    :disabled="modelValue.x.length <= 1"
                    @click="removeX(i)"
                    title="Удалить строку"
                >
                  ×
                </button>
              </div>
            </th>

            <td v-for="(y, j) in modelValue.y" :key="j">
              <input
                  type="number"
                  min="0"
                  max="1"
                  step="any"
                  class="form-control form-control-sm text-center"
                  :value="modelValue.R[i][j]"
                  @input="updateCell(i, j, $event.target.value)"
              >
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <div class="text-muted small">
        Строки соответствуют элементам X, столбцы — элементам Y.
        Значения матрицы R должны находиться в диапазоне от 0 до 1.
      </div>
    </div>
  </div>
</template>

<style scoped>
.relation-table {
  min-width: 350px;
}

.relation-table th {
  min-width: 85px;
}

.relation-table td {
  min-width: 85px;
}

.axis-cell {
  min-width: 85px;
}
</style>