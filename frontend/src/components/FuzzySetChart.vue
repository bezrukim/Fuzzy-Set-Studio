
<script setup>
import { computed } from 'vue'
import { Scatter } from 'vue-chartjs'
import {
  Chart as ChartJS,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
  Title
} from 'chart.js'

ChartJS.register(
    LinearScale,
    PointElement,
    LineElement,
    Tooltip,
    Legend,
    Title
)

const props = defineProps({
  setA: Array,
  setB: Array,
  result: Array,
  showSetB: Boolean
})

const chartData = computed(() => {
  const datasets = [
    {
      label: 'Множество A',
      data: props.setA.map(p => ({ x: Number(p.x), y: Number(p.y) })),
      borderColor: '#2563eb',
      backgroundColor: '#2563eb',
      showLine: true,
      pointRadius: 3
    }
  ]

  if (props.showSetB) {
    datasets.push({
      label: 'Множество B',
      data: props.setB.map(p => ({ x: Number(p.x), y: Number(p.y) })),
      borderColor: '#16a34a',
      backgroundColor: '#16a34a',
      showLine: true,
      pointRadius: 3
    })
  }

  if (props.result?.length) {
    datasets.push({
      label: 'Результат',
      data: props.result.map(p => ({ x: Number(p.x), y: Number(p.y) })),
      borderColor: '#dc2626',
      backgroundColor: '#dc2626',
      showLine: true,
      pointRadius: 2
    })
  }

  return { datasets }
})

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  parsing: false,
  plugins: {
    legend: {
      position: 'bottom'
    }
  },
  scales: {
    x: {
      type: 'linear',
      title: {
        display: true,
        text: 'x'
      }
    },
    y: {
      min: 0,
      max: 1,
      title: {
        display: true,
        text: 'μ(x)'
      }
    }
  }
}
</script>

<template>
  <div style="height: 380px">
    <Scatter
        :data="chartData"
        :options="chartOptions"
    />
  </div>
</template>
