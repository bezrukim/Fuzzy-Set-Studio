<script setup>
import { computed, ref } from 'vue'

import FuzzySetEditor from './components/FuzzySetEditor.vue'
import FuzzySetChart from './components/FuzzySetChart.vue'
import FuzzyRelationEditor from './components/FuzzyRelationEditor.vue'

import { calculateOperation } from './services/fuzzySetApi'
import {
  calculateRelationImage,
  calculateComposition
} from './services/fuzzyRelationApi'

// Режим приложения
const mode = ref('sets')

// Операции над множествами
const operations = [
  { value: 'UNION', label: 'Объединение', binary: true },
  { value: 'INTERSECTION', label: 'Пересечение', binary: true },
  { value: 'NEGATION', label: 'Дополнение', binary: false },
  { value: 'SUBSTRACTION', label: 'Разность', binary: true },
  { value: 'ALGEBRAIC_SUM', label: 'Алгебраическая сумма', binary: true },
  { value: 'ALGEBRAIC_MULTIPLICATION', label: 'Алгебраическое произведение', binary: true }
]

const selectedOperation = ref('UNION')
const step = ref(0.01)

const setA = ref([
  { x: 0, y: 0 },
  { x: 1, y: 1 },
  { x: 2, y: 0 }
])

const setB = ref([
  { x: 0, y: 0.2 },
  { x: 1, y: 0.7 },
  { x: 2, y: 0.1 }
])

const result = ref(null)

const currentOperation = computed(() =>
    operations.find(op => op.value === selectedOperation.value)
)

const isBinary = computed(() =>
    currentOperation.value?.binary ?? true
)

// Операции над отношениями
const relationOperations = [
  { value: 'DIRECT_IMAGE', label: 'Прямой образ' },
  { value: 'SUB_IMAGE', label: 'Нижний образ' },
  { value: 'SUPER_IMAGE', label: 'Верхний образ' },
  { value: 'DIRECT_PREIMAGE', label: 'Прямой прообраз'},
  { value: 'SUB_PREIMAGE', label: 'Нижний прообраз' },
  { value: 'SUPER_PREIMAGE', label: 'Верхний прообраз' }
]

const selectedRelationOperation = ref('DIRECT_IMAGE')

function createRelation() {
  return {
    x: [1, 2, 3],
    y: [1, 2, 3],
    R: [
      [0.2, 0.7, 1.0],
      [0.5, 0.3, 0.8],
      [0.1, 0.9, 0.4]
    ]
  }
}

const relation = ref(createRelation())

const imageSet = ref([
  { x: 1, y: 0.8 },
  { x: 2, y: 0.5 },
  { x: 3, y: 0.3 }
])

const relationResult = ref(null)
const imageResult = ref(null)

// Список отношений для композиции
const compositionRelations = ref([
  createRelation(),
  createRelation()
])

const compositionResult = ref(null)

const loading = ref(false)
const error = ref('')

function validateSet(points, name) {
  if (!points.length) {
    return `${name}: добавьте хотя бы одну точку.`
  }

  for (const point of points) {
    if (
        point.x === '' ||
        point.y === '' ||
        !Number.isFinite(Number(point.x)) ||
        !Number.isFinite(Number(point.y))
    ) {
      return `${name}: координаты должны быть числами.`
    }

    if (Number(point.y) < 0 || Number(point.y) > 1) {
      return `${name}: степень принадлежности должна быть от 0 до 1.`
    }
  }

  const sorted = [...points].sort(
      (a, b) => Number(a.x) - Number(b.x)
  )

  for (let i = 1; i < sorted.length; i++) {
    if (Number(sorted[i].x) === Number(sorted[i - 1].x)) {
      return `${name}: координаты x не должны повторяться.`
    }
  }

  return ''
}

function validateRelation(value, name) {
  if (!value.x.length || !value.y.length) {
    return `${name}: множества X и Y не должны быть пустыми.`
  }

  if (value.R.length !== value.x.length) {
    return `${name}: количество строк матрицы не соответствует X.`
  }

  for (let i = 0; i < value.x.length; i++) {
    if (
        value.x[i] === '' ||
        !Number.isFinite(Number(value.x[i]))
    ) {
      return `${name}: некорректное значение X.`
    }
  }

  for (let j = 0; j < value.y.length; j++) {
    if (
        value.y[j] === '' ||
        !Number.isFinite(Number(value.y[j]))
    ) {
      return `${name}: некорректное значение Y.`
    }
  }

  if (new Set(value.x.map(Number)).size !== value.x.length) {
    return `${name}: координаты X не должны повторяться.`
  }

  if (new Set(value.y.map(Number)).size !== value.y.length) {
    return `${name}: координаты Y не должны повторяться.`
  }

  for (let i = 0; i < value.R.length; i++) {
    if (value.R[i].length !== value.y.length) {
      return `${name}: количество столбцов матрицы не соответствует Y.`
    }

    for (const cell of value.R[i]) {
      if (
          cell === '' ||
          !Number.isFinite(Number(cell)) ||
          Number(cell) < 0 ||
          Number(cell) > 1
      ) {
        return `${name}: значения матрицы должны быть числами от 0 до 1.`
      }
    }
  }

  return ''
}

function normalizeSet(points) {
  return {
    points: [...points]
        .map(p => ({
          x: Number(p.x),
          y: Number(p.y)
        }))
        .sort((a, b) => a.x - b.x)
  }
}

function normalizeRelation(value) {
  return {
    x: value.x.map(Number),
    y: value.y.map(Number),
    R: value.R.map(row => row.map(Number))
  }
}

// Вычисление операций над множествами
async function calculateSet() {
  error.value = ''
  result.value = null

  const errorA = validateSet(setA.value, 'Множество A')

  if (errorA) {
    error.value = errorA
    return
  }

  if (isBinary.value) {
    const errorB = validateSet(setB.value, 'Множество B')

    if (errorB) {
      error.value = errorB
      return
    }
  }

  if (!Number.isFinite(Number(step.value)) || Number(step.value) <= 0) {
    error.value = 'Шаг должен быть положительным конечным числом.'
    return
  }

  const request = {
    operationType: selectedOperation.value,
    setA: normalizeSet(setA.value),
    setB: isBinary.value ? normalizeSet(setB.value) : null,
    step: Number(step.value)
  }

  loading.value = true

  try {
    const response = await calculateOperation(request)
    result.value = response.points
  } catch (err) {
    error.value =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        'Не удалось выполнить вычисление.'
  } finally {
    loading.value = false
  }
}

// Вычисление образа / прообраза
async function calculateImage() {
  error.value = ''
  imageResult.value = null

  const relationError = validateRelation(relation.value, 'Отношение R')

  if (relationError) {
    error.value = relationError
    return
  }

  const setError = validateSet(imageSet.value, 'Множество A')

  if (setError) {
    error.value = setError
    return
  }

  const request = {
    type: selectedRelationOperation.value,
    relationDto: normalizeRelation(relation.value),
    setDto: normalizeSet(imageSet.value)
  }

  loading.value = true

  try {
    const response = await calculateRelationImage(request)
    imageResult.value = response.points
  } catch (err) {
    error.value =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        'Не удалось вычислить образ.'
  } finally {
    loading.value = false
  }
}

// Работа с цепочкой отношений
function addCompositionRelation() {
  compositionRelations.value.push(createRelation())
}

function removeCompositionRelation(index) {
  if (compositionRelations.value.length <= 2) return

  compositionRelations.value.splice(index, 1)
}

async function calculateCompose() {
  error.value = ''
  compositionResult.value = null

  if (compositionRelations.value.length < 2) {
    error.value = 'Для композиции необходимо минимум два отношения.'
    return
  }

  for (let i = 0; i < compositionRelations.value.length; i++) {
    const relationError = validateRelation(
        compositionRelations.value[i],
        `Отношение R${i + 1}`
    )

    if (relationError) {
      error.value = relationError
      return
    }
  }

  // Проверяем совместимость осей соседних отношений
  for (let i = 0; i < compositionRelations.value.length - 1; i++) {
    const current = compositionRelations.value[i]
    const next = compositionRelations.value[i + 1]

    if (
        current.y.length !== next.x.length ||
        current.y.some((value, index) =>
            Number(value) !== Number(next.x[index])
        )
    ) {
      error.value =
          `Отношения R${i + 1} и R${i + 2} несовместимы: ` +
          'ось Y первого отношения должна совпадать с осью X второго.'
      return
    }
  }

  const request = {
    relationDtoList: compositionRelations.value.map(normalizeRelation)
  }

  loading.value = true

  try {
    compositionResult.value = await calculateComposition(request)
  } catch (err) {
    error.value =
        err.response?.data?.message ||
        err.response?.data?.error ||
        err.message ||
        'Не удалось выполнить композицию.'
  } finally {
    loading.value = false
  }
}

function clearResults() {
  result.value = null
  imageResult.value = null
  relationResult.value = null
  compositionResult.value = null
  error.value = ''
}
</script>

<template>
  <main class="container py-4">
    <header class="mb-4">
      <h1 class="fw-bold">Fuzzy Set Studio</h1>
      <p class="text-muted">
        Лаборатория операций над нечёткими множествами и отношениями
      </p>
    </header>

    <!-- Переключение режимов -->
    <div class="btn-group mb-4 w-100" role="group">
      <button
          class="btn"
          :class="mode === 'sets' ? 'btn-primary' : 'btn-outline-primary'"
          @click="mode = 'sets'; clearResults()"
      >
        Нечёткие множества
      </button>

      <button
          class="btn"
          :class="mode === 'relations' ? 'btn-primary' : 'btn-outline-primary'"
          @click="mode = 'relations'; clearResults()"
      >
        Нечёткие отношения
      </button>
    </div>

    <!-- РЕЖИМ НЕЧЁТКИХ МНОЖЕСТВ -->
    <template v-if="mode === 'sets'">
      <section class="row g-3 mb-4">
        <div class="col-12 col-lg-6">
          <FuzzySetEditor
              title="Множество A"
              v-model="setA"
          />
        </div>

        <div v-if="isBinary" class="col-12 col-lg-6">
          <FuzzySetEditor
              title="Множество B"
              v-model="setB"
          />
        </div>
      </section>

      <section class="card mb-4">
        <div class="card-body">
          <h5 class="card-title mb-3">Параметры вычисления</h5>

          <div class="row g-3 align-items-end">
            <div class="col-12 col-md-6">
              <label class="form-label">Операция</label>

              <select
                  class="form-select"
                  v-model="selectedOperation"
              >
                <option
                    v-for="operation in operations"
                    :key="operation.value"
                    :value="operation.value"
                >
                  {{ operation.label }}
                </option>
              </select>
            </div>

            <div class="col-12 col-md-3">
              <label class="form-label">Шаг</label>

              <input
                  type="number"
                  class="form-control"
                  min="0.00001"
                  step="any"
                  v-model.number="step"
              >
            </div>

            <div class="col-12 col-md-3">
              <button
                  class="btn btn-primary w-100"
                  @click="calculateSet"
                  :disabled="loading"
              >
                {{ loading ? 'Вычисление...' : 'Вычислить' }}
              </button>
            </div>
          </div>
        </div>
      </section>

      <section class="card">
        <div class="card-header">
          <strong>График</strong>
        </div>

        <div class="card-body">
          <FuzzySetChart
              :set-a="setA"
              :set-b="setB"
              :result="result"
              :show-set-b="isBinary"
          />

          <div v-if="result" class="alert alert-success mt-3 mb-0">
            Вычисление завершено. Получено точек: {{ result.length }}.
          </div>
        </div>
      </section>
    </template>

    <!-- РЕЖИМ НЕЧЁТКИХ ОТНОШЕНИЙ -->
    <template v-else>
      <section class="card mb-4">
        <div class="card-body">
          <h5 class="card-title">Операции над нечёткими отношениями</h5>

          <div class="btn-group w-100 mt-3">
            <button
                class="btn"
                :class="relationResult === null ? 'btn-primary' : 'btn-outline-primary'"
                @click="selectedRelationOperation = 'DIRECT_IMAGE'; clearResults()"
            >
              Образы
            </button>
          </div>
        </div>
      </section>

      <section class="card mb-4">
        <div class="card-header">
          <strong>Выбор операции</strong>
        </div>

        <div class="card-body">
          <label class="form-label">Тип операции</label>

          <select
              class="form-select"
              v-model="selectedRelationOperation"
              @change="clearResults"
          >
            <option
                v-for="operation in relationOperations"
                :key="operation.value"
                :value="operation.value"
            >
              {{ operation.label }}
            </option>
          </select>
        </div>
      </section>

      <section class="mb-4">
        <FuzzyRelationEditor
            title="Отношение R"
            v-model="relation"
        />
      </section>

      <section class="card mb-4">
        <div class="card-header">
          <strong>Нечёткое множество A</strong>
        </div>

        <div class="card-body">
          <FuzzySetEditor
              title="Множество A"
              v-model="imageSet"
          />
        </div>
      </section>

      <button
          class="btn btn-primary w-100 mb-4"
          @click="calculateImage"
          :disabled="loading"
      >
        {{ loading ? 'Вычисление...' : 'Вычислить образ' }}
      </button>

      <section v-if="imageResult" class="card mb-4">
        <div class="card-header">
          <strong>Результат образа</strong>
        </div>

        <div class="card-body">
          <FuzzySetChart
              :set-a="imageSet"
              :set-b="[]"
              :result="imageResult"
              :show-set-b="false"
          />

          <div class="alert alert-success mt-3 mb-0">
            Вычисление завершено. Получено точек:
            {{ imageResult.length }}.
          </div>
        </div>
      </section>

      <hr class="my-4">

      <!-- КОМПОЗИЦИЯ -->
      <section class="mb-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <div>
            <h4 class="mb-1">Композиция отношений</h4>
            <p class="text-muted mb-0">
              R₁ ∘ R₂ ∘ ... ∘ Rₖ
            </p>
          </div>

          <button
              class="btn btn-outline-primary"
              @click="addCompositionRelation"
          >
            + Добавить отношение
          </button>
        </div>

        <div
            v-for="(item, index) in compositionRelations"
            :key="index"
            class="mb-3"
        >
          <div class="d-flex justify-content-between align-items-center mb-2">
            <strong>Отношение R{{ index + 1 }}</strong>

            <button
                class="btn btn-sm btn-outline-danger"
                :disabled="compositionRelations.length <= 2"
                @click="removeCompositionRelation(index)"
            >
              Удалить
            </button>
          </div>

          <FuzzyRelationEditor
              :title="`Отношение R${index + 1}`"
              v-model="compositionRelations[index]"
          />
        </div>

        <button
            class="btn btn-primary w-100"
            @click="calculateCompose"
            :disabled="loading"
        >
          {{ loading ? 'Вычисление...' : 'Вычислить композицию' }}
        </button>
      </section>

      <!-- РЕЗУЛЬТАТ КОМПОЗИЦИИ -->
      <section v-if="compositionResult" class="card mb-4">
        <div class="card-header">
          <strong>Результат композиции</strong>
        </div>

        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-bordered text-center align-middle">
              <thead>
              <tr>
                <th>X \ Y</th>
                <th v-for="(y, j) in compositionResult.y" :key="j">
                  {{ y }}
                </th>
              </tr>
              </thead>

              <tbody>
              <tr
                  v-for="(x, i) in compositionResult.x"
                  :key="i"
              >
                <th>{{ x }}</th>

                <td
                    v-for="(y, j) in compositionResult.y"
                    :key="j"
                >
                  {{ Number(compositionResult.R[i][j]).toFixed(3) }}
                </td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="alert alert-success mb-0">
            Композиция успешно вычислена.
          </div>
        </div>
      </section>
    </template>

    <!-- ОБЩАЯ ОШИБКА -->
    <div v-if="error" class="alert alert-danger mt-3">
      {{ error }}
    </div>
  </main>
</template>